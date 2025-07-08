package com.example.models;

public class Thesukien implements java.io.Serializable {
    private int id;
    private String title;
    private double price;
    private String location;
    private String date;
    private String description;
    private String organizer;
    private String contact;
    private String timeStart;
    private String dateStart;
    private String timeEnd;
    private String dateEnd;
    private String registrationOpen;
    private String form;
    private String mapUrl;
    private String thumbnail;
    private java.util.List<TicketCategory> ticketCategories;
    private String category;
    private String detailTime;
    private String city;
    private String detailAddress;
    private String mode;
    private int soldTicket;
    private int remainingTicket;
    private String linkMap;


    public Thesukien() {}

    public Thesukien(int id, String title, double price, String location, String date, String description, String organizer, String contact, String timeStart, String dateStart, String timeEnd, String dateEnd, String registrationOpen, String form, String mapUrl, String thumbnail, java.util.List<TicketCategory> ticketCategories, String category, String detailTime, String city, String detailAddress, String mode, int soldTicket, int remainingTicket) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.location = location;
        this.date = date;
        this.description = description;
        this.organizer = organizer;
        this.contact = contact;
        this.timeStart = timeStart;
        this.dateStart = dateStart;
        this.timeEnd = timeEnd;
        this.dateEnd = dateEnd;
        this.registrationOpen = registrationOpen;
        this.form = form;
        this.mapUrl = mapUrl;
        this.thumbnail = thumbnail;
        this.ticketCategories = ticketCategories;
        this.category = category;
        this.detailTime = detailTime;
        this.city = city;
        this.detailAddress = detailAddress;
        this.mode = mode;
        this.soldTicket = soldTicket;
        this.remainingTicket = remainingTicket;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getTimeStart() { return timeStart; }
    public void setTimeStart(String timeStart) { this.timeStart = timeStart; }
    public String getDateStart() { return dateStart; }
    public void setDateStart(String dateStart) { this.dateStart = dateStart; }
    public String getTimeEnd() { return timeEnd; }
    public void setTimeEnd(String timeEnd) { this.timeEnd = timeEnd; }
    public String getDateEnd() { return dateEnd; }
    public void setDateEnd(String dateEnd) { this.dateEnd = dateEnd; }
    public String getRegistrationOpen() { return registrationOpen; }
    public void setRegistrationOpen(String registrationOpen) { this.registrationOpen = registrationOpen; }
    public String getForm() { return form; }
    public void setForm(String form) { this.form = form; }
    public String getMapUrl() { return mapUrl; }
    public void setMapUrl(String mapUrl) { this.mapUrl = mapUrl; }
    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public java.util.List<TicketCategory> getTicketCategories() { return ticketCategories; }
    public void setTicketCategories(java.util.List<TicketCategory> ticketCategories) { this.ticketCategories = ticketCategories; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDetailTime() { return detailTime; }
    public void setDetailTime(String detailTime) { this.detailTime = detailTime; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getDetailAddress() { return detailAddress; }
    public void setDetailAddress(String detailAddress) { this.detailAddress = detailAddress; }
    public int getSoldTicket() { return soldTicket; }
    public void setSoldTicket(int soldTicket) { this.soldTicket = soldTicket; }
    public int getRemainingTicket() { return remainingTicket; }
    public void setRemainingTicket(int remainingTicket) { this.remainingTicket = remainingTicket; }
    public String getMode() {return mode;}
    public void setMode(String mode) {this.mode = mode;}
    public String getLinkMap() { return linkMap; }
    public void setLinkMap(String linkMap) { this.linkMap = linkMap; }

    public static class TicketCategory implements java.io.Serializable {
        private String name;
        private double price;

        public TicketCategory() {}
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
