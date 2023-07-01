package Butlers.Ticat.weather.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class Weather {

    private Double temp;

    private String rainAmount;

    private Double humid;

    private String lastUpdateTime;

    private String sky;
}
