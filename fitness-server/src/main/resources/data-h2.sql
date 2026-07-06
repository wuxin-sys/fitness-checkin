-- H2 初始数据
-- 管理员（密码 admin123，MD5(MD5+salt)加密）
INSERT INTO admin (username, password) VALUES ('admin', 'f01e5700b65288f61d0bce1d666f2d85');

-- 测试用户（密码 123456）
INSERT INTO user (username, password, nickname, gender) VALUES ('zhangsan', 'e44ea21cc06ffd59bcd5ce5c47e18a81', '张三', 1);
INSERT INTO user (username, password, nickname, gender) VALUES ('lisi', 'e44ea21cc06ffd59bcd5ce5c47e18a81', '李四', 1);

-- 测试公告
INSERT INTO notice (title, content) VALUES ('欢迎使用健身打卡系统', '坚持每天打卡，记录你的健身之旅！祝你越来越健康！');
INSERT INTO notice (title, content) VALUES ('系统已上线', '本系统已正式上线运行，如有问题请联系管理员。');

-- 测试打卡数据
INSERT INTO checkin (user_id, checkin_date, sport_type, duration, calorie, remark) VALUES (1, CURRENT_DATE, '跑步', 30, 250, '晨跑');
INSERT INTO checkin (user_id, checkin_date, sport_type, duration, calorie, remark) VALUES (1, CURRENT_DATE, '胸部', 45, 320, '卧推训练');
INSERT INTO checkin (user_id, checkin_date, sport_type, duration, calorie, remark) VALUES (1, DATEADD('DAY', -1, CURRENT_DATE), '腿部', 50, 400, '深蹲');
INSERT INTO checkin (user_id, checkin_date, sport_type, duration, calorie, remark) VALUES (1, DATEADD('DAY', -2, CURRENT_DATE), '背部', 40, 300, '引体向上');
INSERT INTO checkin (user_id, checkin_date, sport_type, duration, calorie, remark) VALUES (2, CURRENT_DATE, '有氧', 60, 500, '游泳');
