package dto;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ErrorMessageDto {
    private String timestamp;
    private String status;
    private String error;
    private String message;
    private String path;
}
