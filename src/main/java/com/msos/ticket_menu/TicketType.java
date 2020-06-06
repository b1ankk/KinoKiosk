package com.msos.ticket_menu;

public enum TicketType
{
    ADULT(34.99f),
    CHILD(20.59f),
    STUDENT(26.99f),
    SENIOR(23.89f);
    
    private float price;
    
    TicketType(float price)
    {
        this.price = price;
    }
    
    public float getPrice()
    {
        return price;
    }
}
