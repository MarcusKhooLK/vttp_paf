package edu.nus.iss.sg.workshop27.repo;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.nus.iss.sg.workshop27.model.LineItem;
import edu.nus.iss.sg.workshop27.model.PurchaseOrder;

@Repository
public class OrderRepository {
    
    @Autowired
    private JdbcTemplate template;

    public int insertPO(final PurchaseOrder po) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(Queries.SQL_INSERT_PO,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, po.getName());
            ps.setString(2, po.getEmail());
            return ps;
        }, keyHolder);

        BigInteger bigInt = (BigInteger)keyHolder.getKey();
        return bigInt.intValue();
    }

    public int[] insertLineItem(Integer orderId, Collection<LineItem> lineItems) {
        List<Object[]> params = lineItems.stream()
            .map(li -> {
                Object[] row = new Object[4];
                row[0] = li.getQty();
                row[1] = orderId;
                row[2] = li.getDescription();
                row[3] = li.getUnitPrice();
                return row;
            })
            .toList();

        return template.batchUpdate(Queries.SQL_INSERT_LINE_ITEM, params);
    }
}
