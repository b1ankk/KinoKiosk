package com.msos.application.tickets;

public enum TicketType
{
    ADULT(34.99),
    CHILD(20.59),
    STUDENT(26.99),
    SENIOR(23.89);
    
    private double price;
    
    TicketType(double price)
    {
        this.price = price;
    }
    
    public double getPrice()
    {
        return price;
    }
}
