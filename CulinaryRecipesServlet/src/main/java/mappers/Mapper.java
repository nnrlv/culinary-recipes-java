package mappers;

public interface Mapper<F, T> {
    T map(F object);
}