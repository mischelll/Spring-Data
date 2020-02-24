package tasks.queries;

public class Queries {
    /*
        These are the queries I used for every task.
        For every task I marked(added a comment) the adjacent queries.
     */

    // 1.GET VILLAINS NAMES
    public static final String GET_VILLAIN_NAME = "select v.name, count(m.id) as count from villains as v\n" +
            "join minions_villains mv on v.id = mv.villain_id\n" +
            "join minions as m on mv.minion_id = m.id\n" +
            "group by v.name\n" +
            "having count > ?\n" +
            "order by count DESC;";

    //2.GET MINION NAMES
    public static final String GET_VILLAIN_NAME_BY_ID = "select v.name from villains as v\n" +
            "where v.id = ?;";
    public static final String GET_MINION_NAME_AGE_BY_VILLAIN_ID = "select m.name, m.age from minions as m\n" +
            "join minions_villains mv on m.id = mv.minion_id\n" +
            "where mv.villain_id = ?;";

    //3.ADD MINION
    public static final String CHECK_MINION_TOWN = "select m.name from towns as m\n" +
            "where m.name = ?;";
    public static final String CHECK_VILLAIN = "select v.name from villains as v\n" +
            "where v.name = ?;";
    public static final String INSERT_VILLAIN = "insert into villains(name, evilness_factor)\n" +
            "values (?, 'evil');";
    public static final String INSERT_MINION_TOWN = "insert into towns(name)\n" +
            "values (?);";
    public static final String INSERT_MINION = "insert into minions(name, age, town_id)\n" +
            "values (?,?,(select t.id from towns as t where t.name = ?));";
    public static final String INSERT_MINION_TO_VILLAIN = "insert into minions_villains (minion_id, villain_id)\n" +
            "values ((select m.id from minions as m where m.name = ?), (select  v.id from villains as v where v.name = ?));";

    //4.Change Town Names Casing
    public static final String SET_TOWN_NAME_TO_UPPERCASE = "update towns \n" +
            "set name = upper(name)\n" +
            "where country = ?;";
    public static final String CHECK_FOR_COUNTRY = "select name from towns\n" +
            "where country = ?;";

    //5.REMOVE VILLAIN
    public static final String RELEASE_MINIONS = "update minions_villains\n" +
            "set minion_id = null\n" +
            "where villain_id = ?;";
    public static final String DELETE_VILLAIN_FROM_MINIONS_VILLAINS = "delete from minions_villains\n" +
            "where villain_id = ?;";
    public static  final String  DELETE_VILLAIN_FROM_VILLAINS = "delete from villains\n" +
            "where id = ?;" ;

    //6.PRINT ALL MINION NAMES
    public static final String SELECT_ALL_NAMES_FROM_MINIONS = "select name from minions;";
    public static final String GET_COUNT_OF_MINIONS = "select count(m.id) as count from minions as m;";

    //7.INCREASE MINIONS AGE
    public static final String GET_MINION_NAME_BY_ID = "select  m.name from minions as m\n" +
            "where m.id = ?;\n";
    public static final String SET_NAME_TO_LOWERCASE_INCREASE_AGE = "update minions\n" +
            "set name = lower(name), age = age + 1\n" +
            "where name =  ?;";
    public static final String SELECT_MINIONS_NAME_AGE = "select m.name, m.age from minions as m;";

    //8.INCREASE AGE STORED PROCEDURE
    private static final String PROCEDURE = "DELIMITER $$\n" +
            "create procedure usp_get_older(minion_id int)\n" +
            "begin\n" +
            "    update minions\n" +
            "    set age = age + 1\n" +
            "    where id = minion_id;\n" +
            "end $$\n";
    public static final String CALL_PROCEDURE = "call usp_get_older(?);";
    public static final String SELECT_NAME_AGE = "select name, age from minions\n" +
            "where id = ?;";









}
