use book_shop_db;
drop procedure if exists udp_author_total_books_written;
DELIMITER //

CREATE PROCEDURE udp_author_total_books_written( in first_name varchar(255), in last_name varchar(255),  out b_count int)
BEGIN

     set b_count := (select count(*) as books_count
                   from books as b
                            join authors a on b.author_id = a.id
                   where a.first_name = first_name
                     and a.last_name = last_name
                   group by author_id);

END //

DELIMITER ;

call udp_author_total_books_written('Chris', 'Graham',@b_count);
select @b_count;