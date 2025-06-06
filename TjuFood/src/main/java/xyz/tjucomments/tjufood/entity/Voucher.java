package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Voucher {
    private Long id;
    private String title;
    private String description;
    private Integer type;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal minAmount;
    private Long canteenId;
    private Long stallId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer stock;
    private Integer requiredCredits;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
