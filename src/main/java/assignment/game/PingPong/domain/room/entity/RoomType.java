package assignment.game.PingPong.domain.room.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {
    SINGLE { // 단식
        @Override
        public int getCapacity() {
            return 2; // 단식: 최대 2명
        }
    },
    DOUBLE { // 복식
        @Override
        public int getCapacity() {
            return 4; // 복식: 최대 4명
        }
    };

    public abstract int getCapacity();
}
