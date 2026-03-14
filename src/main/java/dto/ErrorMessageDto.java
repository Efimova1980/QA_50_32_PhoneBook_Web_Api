package dto;


import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ErrorMessageDto {
    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
