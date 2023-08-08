package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyWriteDTO {
    private Integer boardId; // * 인트는 null체크 못한다. */
    private String comment;
}
