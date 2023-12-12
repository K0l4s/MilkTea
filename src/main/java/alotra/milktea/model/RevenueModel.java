package alotra.milktea.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueModel {
    private Date startTime;
    private Date endTime;

    private double revenue;
}
