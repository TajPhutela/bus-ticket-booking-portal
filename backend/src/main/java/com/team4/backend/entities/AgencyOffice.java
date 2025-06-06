package com.team4.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agency_offices")
public class AgencyOffice {
    @Id
    @Column(name = "office_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @Size(max = 100)
    @Column(name = "office_mail", length = 100)
    private String officeMail;

    @Size(max = 50)
    @Column(name = "office_contact_person_name", length = 50)
    private String officeContactPersonName;

    @Size(max = 10)
    @Column(name = "office_contact_number", length = 10)
    private String officeContactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_address_id")
    private Address officeAddress;

}