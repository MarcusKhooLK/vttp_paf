package edu.nus.iss.sg.workshop27.exception;

import edu.nus.iss.sg.workshop27.model.PurchaseOrder;

public class OrderTooLargeException extends Exception {
    private PurchaseOrder po;

    public PurchaseOrder getPo() {
        return po;
    }

    public void setPo(PurchaseOrder po) {
        this.po = po;
    }

    public OrderTooLargeException() {
        super();
    }

    public OrderTooLargeException(String msg) {
        super(msg);
    }
    
}
