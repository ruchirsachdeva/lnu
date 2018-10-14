package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TherapyList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long therapyListID;
  private String name;
  @ManyToOne
  @JoinColumn(name = "medicine_idmedicine")
  private Medicine medicine;
  private String dosage;


}
