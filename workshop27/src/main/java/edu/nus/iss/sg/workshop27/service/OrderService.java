package edu.nus.iss.sg.workshop27.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nus.iss.sg.workshop27.exception.OrderTooLargeException;
import edu.nus.iss.sg.workshop27.model.LineItem;
import edu.nus.iss.sg.workshop27.model.PurchaseOrder;
import edu.nus.iss.sg.workshop27.repo.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository repo;

    @Transactional(rollbackFor = OrderTooLargeException.class)
    public Integer createPurchaseOrder(final PurchaseOrder po) throws OrderTooLargeException {

        // insert purchase order
        final Integer orderId = repo.insertPO(po);

        double totalPrice = 0.0f;
        for(LineItem li : po.getLineItems()) {
            totalPrice += li.getQty() * li.getUnitPrice();

            if(totalPrice >= 10000) {
                OrderTooLargeException ex = new OrderTooLargeException("Order exceed 10000: %f".formatted(totalPrice));
                ex.setPo(po);
                throw ex;
            }
        }

        // insert line items
        repo.insertLineItem(orderId, po.getLineItems());

        return orderId;
    }
}
