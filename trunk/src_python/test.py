#!/usr/bin/env python
# all the imports
from __future__ import with_statement
import sqlite3
from flask import Flask, request, session, g, redirect, url_for, \
                  abort, render_template, flash

from contextlib import closing

# configuration
DATABASE = './flaskr.db'
DEBUG = True
SECRET_KEY = 'development key'
USERNAME = 'admin'
PASSWORD = 'default'

# create our little application :)
app = Flask(__name__)
app.config.from_object(__name__)
app.config.from_envvar('FLASKR_SETTINGS', silent=True)

def connect_db():
  return sqlite3.connect(app.config['DATABASE'])

def init_db():
  with closing(connect_db()) as db:
    with app.open_resource('schema.sql') as f:
      db.cursor().executescript(f.read())
    db.commit()

@app.before_request
def before_request():
    g.db = connect_db()

@app.after_request
def after_request(response):
    g.db.close()
    return response

@app.route('/')
def home():
  return render_template('home.html.jinja2')

@app.route('/list/')
def list_departures():
  cur = g.db.execute('select starts_from, goes_to, ts from departures order by id desc')
  departures = [dict(starts_from=row[0], goes_to=row[1], ts=row[2]) for row in cur.fetchall()]
  print departures
  return render_template('list_departures.html.jinja2', departures=departures)

@app.route('/add/', methods=['GET'])
def add_departure():
  return render_template('add_departure.html.jinja2')

@app.route('/add/', methods=['POST'])
def add_departure_do():
  g.db.execute('insert into departures (starts_from, goes_to, ts) values (?, ?, ?)',
               [request.form['starts_from'], request.form['goes_to'], request.form['ts']])
  g.db.commit()
  flash('New departure was successfully posted')
  return redirect(url_for('list_departures'))

if __name__ == '__main__':
  app.run(debug=True)

