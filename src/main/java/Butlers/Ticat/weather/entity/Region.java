package Butlers.Ticat.weather.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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

    private int nx;

    private int ny;

    public Region(Long id, String parentRegion, String childRegion, int nx, int ny) {
        this.id = id;
        this.parentRegion = parentRegion;
        this.childRegion = childRegion;
        this.nx = nx;
        this.ny = ny;
    }
}
