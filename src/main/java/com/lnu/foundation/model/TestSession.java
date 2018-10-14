package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TestSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long testSessionID;
  private long testType;
  @OneToOne
  @JoinColumn(name = "test_idtest")
  private Test test;
  private String dataURL;


}
