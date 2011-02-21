\. ./sims.v2.drop.sql

delimiter //

\. ./procedures/sims.v2.procedure.criterion.sql
\. ./procedures/sims.v2.procedure.position.sql
\. ./procedures/sims.v2.procedure.route.sql
\. ./procedures/sims.v2.procedure.user.sql
\. ./procedures/sims.v2.procedure.comment.sql

delimiter ;


\. ./sims.v2.create.sql
\. ./sims.v2.insert.sql
