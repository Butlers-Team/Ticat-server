package Butlers.Ticat.weather.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class Region {
    @Id
    @Column(name = "region_id")
    private Long id;
    @Column(name = "region_parent")
    private String parentRegion;
    @Column(name = "region_child")
    private String childRegion;
    private double nx;
    private double ny;

    @Embedded
    private Weather weather;

    public Region(Long id, String parentRegion, String childRegion, double nx, double ny) {
        this.id = id;
        this.parentRegion = parentRegion;
        this.childRegion = childRegion;
        this.nx = nx;
        this.ny = ny;
    }

    public void updateRegionWeather(Weather weather){
        this.weather = weather;
    }

}
