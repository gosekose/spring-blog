package blog.exception.controller.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class RegisterDto {

    @NotNull
    private String email;

    @NotNull
    @Length(min = 10, max = 100, message = "password는 10자 이상입니다.")
    private String password;

}
