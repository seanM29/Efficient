package com.kiminonawa.mydiary.contacts;

public class ContactsEntity {

    private long contactsId;
    private String name;
    private String phoneNumber;
    private String photo;
    private String sortLetters;


    public ContactsEntity(long contactsId, String name, String phoneNumber, String photo) {
        this.contactsId = contactsId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    public long getContactsId() {
        return contactsId;
    }


    public String getName() {
        return name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

}


