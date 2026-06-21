-- ============================================
-- 校运动会管理系统 - 数据库建库脚本
-- 符合第三范式，11张表
-- ============================================

DROP DATABASE IF EXISTS sports_meeting;
CREATE DATABASE sports_meeting DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sports_meeting;

CREATE TABLE department (
    dept_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '院系ID',
    dept_name VARCHAR(50) NOT NULL UNIQUE COMMENT '院系名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='院系表';

CREATE TABLE class (
    class_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '班级ID',
    class_name VARCHAR(50) NOT NULL COMMENT '班级名称',
    dept_id BIGINT NOT NULL COMMENT '所属院系ID',
    grade VARCHAR(10) NOT NULL COMMENT '年级',
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

CREATE TABLE athlete (
    athlete_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '运动员ID',
    name VARCHAR(30) NOT NULL COMMENT '姓名',
    gender CHAR(1) NOT NULL COMMENT '性别',
    class_id BIGINT NOT NULL COMMENT '所属班级ID',
    group_name VARCHAR(20) NOT NULL DEFAULT '甲组' COMMENT '运动员分组',
    phone VARCHAR(20) COMMENT '联系电话',
    FOREIGN KEY (class_id) REFERENCES class(class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运动员表';

CREATE TABLE event (
    event_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '项目ID',
    event_name VARCHAR(50) NOT NULL COMMENT '项目名称',
    event_type VARCHAR(10) NOT NULL COMMENT '径赛/田赛',
    gender_group VARCHAR(10) NOT NULL COMMENT '男子组/女子组',
    unit VARCHAR(10) NOT NULL COMMENT '计量单位',
    record DECIMAL(10,2) COMMENT '校纪录'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='比赛项目表';

CREATE TABLE match_session (
    session_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '场次ID',
    event_id BIGINT NOT NULL COMMENT '比赛项目ID',
    session_name VARCHAR(100) NOT NULL COMMENT '场次名称',
    match_date DATE NOT NULL COMMENT '比赛日期',
    match_time TIME NOT NULL COMMENT '比赛时间',
    venue VARCHAR(100) NOT NULL COMMENT '比赛地点',
    status VARCHAR(10) NOT NULL DEFAULT '未开始' COMMENT '状态',
    FOREIGN KEY (event_id) REFERENCES event(event_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='比赛场次表';

CREATE TABLE participation (
    participation_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '参赛记录ID',
    session_id BIGINT NOT NULL COMMENT '比赛场次ID',
    athlete_id BIGINT NOT NULL COMMENT '运动员ID',
    track_no INT NOT NULL COMMENT '赛道号',
    result DECIMAL(10,2) COMMENT '比赛成绩',
    rank INT COMMENT '名次',
    score INT DEFAULT 0 COMMENT '积分',
    FOREIGN KEY (session_id) REFERENCES match_session(session_id),
    FOREIGN KEY (athlete_id) REFERENCES athlete(athlete_id),
    UNIQUE KEY uk_session_track (session_id, track_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参赛记录表';

CREATE TABLE referee (
    referee_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '裁判ID',
    name VARCHAR(30) NOT NULL COMMENT '姓名',
    role VARCHAR(30) NOT NULL DEFAULT '裁判员' COMMENT '角色'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='裁判表';

CREATE TABLE referee_assignment (
    assignment_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分配ID',
    referee_id BIGINT NOT NULL COMMENT '裁判ID',
    session_id BIGINT NOT NULL COMMENT '场次ID',
    duty VARCHAR(50) NOT NULL COMMENT '职责',
    FOREIGN KEY (referee_id) REFERENCES referee(referee_id),
    FOREIGN KEY (session_id) REFERENCES match_session(session_id),
    UNIQUE KEY uk_referee_session (referee_id, session_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='裁判分配表';

CREATE TABLE supply (
    supply_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '物品ID',
    name VARCHAR(50) NOT NULL COMMENT '物品名称',
    quantity INT NOT NULL DEFAULT 0 COMMENT '数量',
    unit VARCHAR(10) NOT NULL DEFAULT '个' COMMENT '单位'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后勤物品表';

CREATE TABLE staff (
    staff_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '人员ID',
    name VARCHAR(30) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) COMMENT '联系电话'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后勤人员表';

CREATE TABLE duty_schedule (
    duty_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '值班ID',
    staff_id BIGINT NOT NULL COMMENT '后勤人员ID',
    duty_date DATE NOT NULL COMMENT '值班日期',
    start_time TIME NOT NULL COMMENT '开始时间',
    end_time TIME NOT NULL COMMENT '结束时间',
    location VARCHAR(100) NOT NULL COMMENT '值班地点',
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='值班表';

CREATE TABLE article (
    article_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '文章ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    author_name VARCHAR(30) NOT NULL COMMENT '作者姓名',
    dept_id BIGINT NOT NULL COMMENT '作者院系ID',
    publish_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宣传文章表';

-- ============================================
-- 样例数据
-- ============================================

INSERT INTO department (dept_name) VALUES ('计算机科学与技术学院'), ('电子信息工程学院'), ('机械工程学院'), ('经济管理学院'), ('外国语学院'), ('理学院');

INSERT INTO class (class_name, dept_id, grade) VALUES
('计科2301', 1, '2023级'), ('计科2302', 1, '2023级'), ('计科2401', 1, '2024级'),
('电信2301', 2, '2023级'), ('电信2401', 2, '2024级'),
('机械2301', 3, '2023级'), ('机械2401', 3, '2024级'),
('经管2301', 4, '2023级'), ('经管2401', 4, '2024级'),
('英语2301', 5, '2023级'), ('英语2401', 5, '2024级'),
('数学2301', 6, '2023级'), ('数学2401', 6, '2024级');

INSERT INTO athlete (name, gender, class_id, group_name, phone) VALUES
('张伟', 'M', 1, '甲组', '13800001001'), ('李明', 'M', 1, '甲组', '13800001002'),
('王芳', 'F', 2, '甲组', '13800001003'), ('刘洋', 'M', 2, '乙组', '13800001004'),
('赵敏', 'F', 3, '甲组', '13800001005'), ('孙涛', 'M', 3, '甲组', '13800001006'),
('周杰', 'M', 4, '甲组', '13800001007'), ('吴桐', 'F', 4, '甲组', '13800001008'),
('郑宇', 'M', 5, '乙组', '13800001009'), ('林雪', 'F', 5, '甲组', '13800001010'),
('何强', 'M', 6, '甲组', '13800001011'), ('谢晓', 'F', 6, '甲组', '13800001012'),
('陈刚', 'M', 7, '甲组', '13800001013'), ('徐静', 'F', 7, '甲组', '13800001014'),
('苏宁', 'M', 8, '甲组', '13800001015'), ('黄蕾', 'F', 8, '甲组', '13800001016'),
('马超', 'M', 9, '甲组', '13800001017'), ('韩冰', 'F', 9, '甲组', '13800001018'),
('杨光', 'M', 10, '甲组', '13800001019'), ('柳青', 'F', 10, '甲组', '13800001020'),
('胡兵', 'M', 11, '乙组', '13800001021'), ('姜琳', 'F', 11, '甲组', '13800001022'),
('罗浩', 'M', 12, '甲组', '13800001023'), ('唐颖', 'F', 12, '甲组', '13800001024'),
('曹磊', 'M', 13, '甲组', '13800001025'), ('秦雪', 'F', 13, '甲组', '13800001026');

INSERT INTO event (event_name, event_type, gender_group, unit, record) VALUES
('100米', '径赛', '男子组', '秒', 10.50),
('100米', '径赛', '女子组', '秒', 12.30),
('200米', '径赛', '男子组', '秒', 21.80),
('200米', '径赛', '女子组', '秒', 25.20),
('400米', '径赛', '男子组', '秒', 49.00),
('800米', '径赛', '女子组', '秒', 130.00),
('1500米', '径赛', '男子组', '分', 260.00),
('4x100米接力', '径赛', '男子组', '秒', 43.00),
('4x100米接力', '径赛', '女子组', '秒', 50.00),
('跳高', '田赛', '男子组', '米', 2.10),
('跳高', '田赛', '女子组', '米', 1.75),
('跳远', '田赛', '男子组', '米', 7.50),
('跳远', '田赛', '女子组', '米', 6.20),
('铅球', '田赛', '男子组', '米', 15.00),
('铅球', '田赛', '女子组', '米', 12.00);

INSERT INTO match_session (event_id, session_name, match_date, match_time, venue, status) VALUES
(1,  '男子100米预赛第1组', '2026-05-15', '09:00:00', '田径场跑道1', '已结束'),
(1,  '男子100米预赛第2组', '2026-05-15', '09:15:00', '田径场跑道1', '已结束'),
(2,  '女子100米预赛第1组', '2026-05-15', '09:30:00', '田径场跑道1', '已结束'),
(10, '男子跳高决赛',     '2026-05-15', '10:00:00', '跳高区',       '已结束'),
(12, '男子跳远决赛',     '2026-05-15', '14:00:00', '跳远沙坑',     '进行中'),
(8,  '男子4x100米接力决赛','2026-05-16','09:00:00', '田径场跑道1', '未开始'),
(6,  '女子800米决赛',    '2026-05-16', '10:00:00', '田径场跑道1', '未开始');

INSERT INTO participation (session_id, athlete_id, track_no, result, rank, score) VALUES
(1, 1,  1, 11.20, 1, 9),  (1, 4,  2, 11.50, 2, 7),  (1, 7,  3, 11.80, 3, 6),
(1, 9,  4, 12.00, 4, 5),  (1, 11, 5, 12.30, 5, 4),  (1, 13, 6, 12.50, 6, 3),
(1, 15, 7, 12.80, 7, 2),  (1, 17, 8, 13.00, 8, 1);

INSERT INTO referee (name, role) VALUES
('张老师', '裁判长'), ('李文', '裁判员'), ('王刚', '计时员'),
('刘强', '发令员'),  ('陈芳', '裁判员'), ('赵华', '计时员');

INSERT INTO referee_assignment (referee_id, session_id, duty) VALUES
(1, 1, '总裁判'), (2, 1, '计时'), (3, 1, '计时'), (4, 1, '发令'),
(1, 4, '总裁判'), (5, 4, '测量'), (6, 4, '记录');

INSERT INTO supply (name, quantity, unit) VALUES
('饮用水', 200, '箱'), ('急救包', 20, '套'), ('号码布', 500, '张'),
('奖牌', 100, '枚'), ('帐篷', 10, '顶'), ('桌椅', 50, '套');

INSERT INTO staff (name, phone) VALUES
('王后勤', '13900001001'), ('陈后勤', '13900001002'),
('张后勤', '13900001003'), ('李后勤', '13900001004');

INSERT INTO duty_schedule (staff_id, duty_date, start_time, end_time, location) VALUES
(1, '2026-05-15', '08:00:00', '12:00:00', '田径场入口'),
(2, '2026-05-15', '12:00:00', '17:00:00', '田径场入口'),
(3, '2026-05-16', '08:00:00', '12:00:00', '篮球场'),
(4, '2026-05-16', '12:00:00', '17:00:00', '篮球场');

INSERT INTO article (title, content, author_name, dept_id) VALUES
('校运动会开幕 计科学院拔得头筹', '5月15日上午，校运动会在田径场隆重开幕...（详细报道略）', '宣传组甲', 1),
('电信学院运动员破校纪录', '在今日下午的男子100米比赛中，电信学院选手张伟以11.20秒刷新校纪录...', '宣传组乙', 2),
('运动场上的幕后英雄', '赛场的每一个角落，都有后勤人员和裁判员默默付出的身影...', '宣传组丙', 5);

-- ============================================
-- 存储过程：自动积分排名（按成绩排序分配名次和积分）
-- ============================================
DELIMITER //
CREATE PROCEDURE sp_auto_rank(IN p_session_id BIGINT)
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_id BIGINT;
    DECLARE v_rank INT DEFAULT 1;

    -- 定义游标，按径赛/田赛规则排序
    DECLARE cur CURSOR FOR
        SELECT participation_id 
        FROM participation
        WHERE session_id = p_session_id AND result IS NOT NULL
        ORDER BY 
            -- 径赛：成绩越小排名越高（升序）
            CASE 
                WHEN (SELECT event_type FROM event e JOIN match_session ms ON e.event_id = ms.event_id WHERE ms.session_id = p_session_id) = '径赛' 
                THEN result 
            END ASC,
            -- 田赛：成绩越大排名越高（降序）
            CASE 
                WHEN (SELECT event_type FROM event e JOIN match_session ms ON e.event_id = ms.event_id WHERE ms.session_id = p_session_id) = '田赛' 
                THEN result 
            END DESC;

    -- 游标结束时设置done为1
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_id;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- 更新名次和积分
        UPDATE participation 
        SET rank = v_rank,
            score = CASE 
                WHEN v_rank = 1 THEN 9
                WHEN v_rank = 2 THEN 7
                WHEN v_rank = 3 THEN 6
                WHEN v_rank = 4 THEN 5
                WHEN v_rank = 5 THEN 4
                WHEN v_rank = 6 THEN 3
                WHEN v_rank = 7 THEN 2
                WHEN v_rank = 8 THEN 1 
                ELSE 0 END
        WHERE participation_id = v_id;

        SET v_rank = v_rank + 1;
    END LOOP;
    CLOSE cur;

    -- 标记比赛已结束
    UPDATE match_session SET status = '已结束' WHERE session_id = p_session_id;
END //
DELIMITER ;

-- ============================================
-- 视图：个人积分排名
-- ============================================
CREATE VIEW v_athlete_ranking AS
SELECT
    a.athlete_id,
    a.name AS athlete_name,
    a.gender,
    a.group_name,
    c.class_name,
    d.dept_name,
    COALESCE(SUM(p.score), 0) AS total_score,
    COUNT(p.participation_id) AS event_count
FROM athlete a
JOIN class c ON a.class_id = c.class_id
JOIN department d ON c.dept_id = d.dept_id
LEFT JOIN participation p ON a.athlete_id = p.athlete_id
GROUP BY a.athlete_id, a.name, a.gender, a.group_name, c.class_name, d.dept_name
ORDER BY total_score DESC;

-- ============================================
-- 视图：院系积分排名
-- ============================================
CREATE VIEW v_dept_ranking AS
SELECT
    d.dept_id,
    d.dept_name,
    COALESCE(SUM(p.score), 0) AS total_score,
    COUNT(DISTINCT a.athlete_id) AS athlete_count,
    COUNT(DISTINCT p.participation_id) AS participation_count
FROM department d
LEFT JOIN class c ON d.dept_id = c.dept_id
LEFT JOIN athlete a ON c.class_id = a.class_id
LEFT JOIN participation p ON a.athlete_id = p.athlete_id
GROUP BY d.dept_id, d.dept_name
ORDER BY total_score DESC;
