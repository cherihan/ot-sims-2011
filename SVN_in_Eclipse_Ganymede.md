# Introduction #

How to add SVN support in Eclipse **_Ganymede_** - For those who want to use SVN under Eclipse

# Details #
## Step 1 ##
1.1 Add source http://download.eclipse.org/technology/subversive/0.7/update-site/ to your available software list in Eclipse (Help->Software Updates...->Available Software->Add Site...)
<br />1.2 Expand the site and Eclipse will load/update the content automatically. Select _Subversive SVN Team Provider Plugin (Incubation)_ under _Subversive Site_
<br />1.3 Click **Install...** and finish the installation progress. You will be demanded to restart the Eclipse when the installation is finished, follow the instruction.
## Step 2 ##
2.1 Just like 1.1 in Step 1 Add source http://community.polarion.com/projects/subversive/download/eclipse/2.0/update-site/ to your available software list
<br />2.2 Expand the site and Eclipse will load/update the content automatically. Select _SVNKit 1.3.5 Implementation (Optional)_ under _Update Site_
<br />2.3 Click **Install...** and finish the installation progress. You will be demanded to restart the Eclipse when the installation is finished, follow the instruction.
## Step 3 ##
3.1 Now you are ready to use SVN under Eclipse. Create a new SVN project : File->New->Project...->SVN->Project from SVN...
<br />3.2 Click **Next**, in the configuration panel fill the **URL** with https://ot-sims-2011.googlecode.com/svn/ and enter your account name and password (_NOTE: This is the password for your SVN commit not the one for your login account_)
<br />3.3 Click next, in the _SELECT RESOURCE_ panel, fill the **URL** with https://ot-sims-2011.googlecode.com/svn/trunk/Covoiturage
<br />3.4 Click **Finish**. After all the files have been fetched you have to resolve some linking problems (especially the missing JsfLib) before you can work on the project