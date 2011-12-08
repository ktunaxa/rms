
insert into ACT_ID_GROUP (ID_, REV_, NAME_, TYPE_) values ( 'treatyEvaluator', 1, 'treatyEvaluator', 'assignment');
insert into ACT_ID_GROUP (ID_, REV_, NAME_, TYPE_) values ( 'culturalEvaluator', 1, 'culturalEvaluator', 'assignment');
insert into ACT_ID_GROUP (ID_, REV_, NAME_, TYPE_) values ( 'terrestrialEvaluator', 1, 'terrestrialEvaluator', 'assignment');
insert into ACT_ID_GROUP (ID_, REV_, NAME_, TYPE_) values ( 'aquaticEvaluator', 1, 'aquaticEvaluator', 'assignment');
insert into ACT_ID_GROUP (ID_, REV_, NAME_, TYPE_) values ( 'archaeologyEvaluator', 1, 'archaeologyEvaluator', 'assignment');
insert into ACT_ID_GROUP (ID_, REV_, NAME_, TYPE_) values ( 'communityManager', 1, 'communityManager', 'assignment');
insert into ACT_ID_GROUP (ID_, REV_, NAME_, TYPE_) values ( 'referralManager', 1, 'referralManager', 'assignment');

insert into ACT_ID_USER values ('KtBpmAdmin', 1, 'Ktunaxa', 'BPM Admin', 'admin@localhost', 'nasukin3', null);
insert into ACT_ID_MEMBERSHIP values ('KtBpmAdmin', 'admin');
insert into ACT_ID_MEMBERSHIP values ('KtBpmAdmin', 'manager');
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'KtBpmAdmin', 'treatyEvaluator' );
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'KtBpmAdmin', 'culturalEvaluator' );
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'KtBpmAdmin', 'terrestrialEvaluator' );
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'KtBpmAdmin', 'aquaticEvaluator' );
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'KtBpmAdmin', 'archaeologyEvaluator' );
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'KtBpmAdmin', 'communityManager' );
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'KtBpmAdmin', 'referralManager' );

insert into ACT_ID_USER values ('refman', 1, 'Referral', 'Manager', 'refman@localhost', 'namfer', null);
insert into ACT_ID_MEMBERSHIP values ('refman', 'user');
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'refman', 'referralManager' );

insert into ACT_ID_USER values ('treaty', 1, 'Treaty', 'Evaluator', 'treaty@localhost', 'ytaert', null);
insert into ACT_ID_MEMBERSHIP values ('treaty', 'user');
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'treaty', 'treatyEvaluator' );

insert into ACT_ID_USER values ('cultural', 1, 'Cultural', 'Evaluator', 'cultural@localhost', 'larutluc', null);
insert into ACT_ID_MEMBERSHIP values ('cultural', 'user');
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'cultural', 'culturalEvaluator' );

insert into ACT_ID_USER values ('terrestrial', 1, 'Terrestrial', 'Evaluator', 'terrestrial@localhost', 'ygoloce', null);
insert into ACT_ID_MEMBERSHIP values ('terrestrial', 'user');
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'terrestrial', 'terrestrialEvaluator' );

insert into ACT_ID_USER values ('aquatic', 1, 'Aquatic', 'Evaluator', 'aquatic@localhost', 'citauqa', null);
insert into ACT_ID_MEMBERSHIP values ('aquatic', 'user');
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'aquatic', 'aquaticEvaluator' );

insert into ACT_ID_USER values ('arch', 1, 'Aquatic', 'Evaluator', 'arch@localhost', 'hcra', null);
insert into ACT_ID_MEMBERSHIP values ('arch', 'user');
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'arch', 'archaeologyEvaluator' );

insert into ACT_ID_USER values ('comm', 1, 'Aquatic', 'Evaluator', 'aquatic@localhost', 'mmoc', null);
insert into ACT_ID_MEMBERSHIP values ('comm', 'user');
insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( 'comm', 'communityManager' );
