package yongda;

public class CalculateService implements CalculateFacade {

    @Override
    public int sum(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public int minus(Integer a, Integer b) {
        return a - b;
    }
}
