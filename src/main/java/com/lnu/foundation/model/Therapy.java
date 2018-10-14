package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Therapy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long therapyID;
  @ManyToOne
  @JoinColumn(name = "user_idpatient")
  private User patient;
  @ManyToOne
  @JoinColumn(name = "user_idmed")
  private User med;
  @ManyToOne
  @JoinColumn(name = "therapylist_idtherapylist")
  private TherapyList therapylist;

}
