package microservices.book.multiplication.domain;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class Multiplication {

    private final int factorA;
    private final int factorB;

    public Multiplication() {
        this(0, 0);
    }
}
