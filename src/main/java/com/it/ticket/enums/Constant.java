package com.it.ticket.enums;

public class Constant {
    
    public enum ResponseStatus {
        SUCCESS("Success"),
        ERROR("Error"),
        WARNING("Warning"),
        PENDING("Pending"),
        FAILED("Failed"),
        PROCESSING("Processing"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled"),
        REJECTED("Rejected"),
        APPROVED("Approved");

        private final String value;

        ResponseStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static final String TICKET_EVENT_REQUEST = "ticket-event-request";
    public static final String TICKET_EVENT_RESPONSE = "ticket-event-response";
    public static final String EVENT_GROUP = "helpdesk-gateway";
    public static final String OPERATION_GET_ALL_CATEGORIES = "get-all-categories"; 
    public static final String OPERATION_GET_ALL_PRIORITIES = "get-all-priorities"; 
    public static final String OPERATION_GET_ALL_STATUSES = "get-all-statuses"; 

 

}
