package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long testID;
  private Date dateTime;
 // private long therapyIDtherapy;
  @ManyToOne
  @JoinColumn(name = "therapy_idtherapy")
  private Therapy therapy;

}
