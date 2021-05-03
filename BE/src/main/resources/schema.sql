drop table if exists team;
create table team
(
    id   int auto_increment primary key,
    name varchar(64) unique not null
);

drop table if exists team_game_score;
create table team_game_score
(
    id       int auto_increment primary key,
    inning   int,
    score    int,
    team     int references team (id),
    team_key int
);

drop table if exists player;
create table player
(
    id   bigint(20) auto_increment primary key,
    name varchar(50),
    team bigint(20) references team(id)
);

drop table if exists player_game_info;
create table player_game_info
(
    id               bigint(20) auto_increment primary key,
    average          bigint(20),
    batting_order    int,
    plate_appearance int,
    hits             int,
    `out`            int,
    player           bigint(20) references player(id)
);

