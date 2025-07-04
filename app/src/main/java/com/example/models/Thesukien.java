package com.example.models;

public class Thesukien {
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
    private int soldTicket;
    private int remainingTicket;

    public Thesukien() {}

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

    public static class TicketCategory {
        private String name;
        private double price;

        public TicketCategory() {}
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
