import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorView view = new CalculatorView();
            CalculatorPresenter presenter = new CalculatorPresenter(view);
            presenter.start();
        });
    }

    // Модель (Model) для калькулятора
    public static class CalculatorModel {
        // Методы для выполнения арифметических операций
        public double add(double number1, double number2) {
            return number1 + number2;
        }

        public double subtract(double number1, double number2) {
            return number1 - number2;
        }

        public double multiply(double number1, double number2) {
            return number1 * number2;
        }

        public double divide(double number1, double number2) throws ArithmeticException {
            if (number2 != 0) {
                return number1 / number2;
            } else {
                throw new ArithmeticException("Ошибка: Нельзя делить на ноль.");
            }
        }
    }

    // Представление (View) для калькулятора с графическим интерфейсом
    public static class CalculatorView extends JFrame {
        private JTextField numberField1;
        private JTextField numberField2;
        private JLabel resultLabel;
        private JComboBox<String> operatorComboBox;
        private JButton calculateButton;

        public CalculatorView() {
            setTitle("Калькулятор"); // Заголовок окна
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Действие при закрытии окна
            setLayout(new FlowLayout()); // Определение компоновки элементов интерфейса

            // Создание элементов интерфейса
            numberField1 = new JTextField(10);
            numberField2 = new JTextField(10);
            resultLabel = new JLabel("Результат: ");
            operatorComboBox = new JComboBox<>(new String[]{"+", "-", "*", "/"}); // Выпадающий список с операциями
            calculateButton = new JButton("Вычислить"); // Кнопка для вычисления результата

            // Обработчик события нажатия на кнопку "Вычислить"
            calculateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String operator = (String) operatorComboBox.getSelectedItem(); // Получаем выбранную операцию
                    double num1 = Double.parseDouble(numberField1.getText()); // Получаем первое число
                    double num2 = Double.parseDouble(numberField2.getText()); // Получаем второе число

                    try {
                        double result = calculateResult(num1, num2, operator); // Вычисляем результат
                        resultLabel.setText("Результат: " + result); // Выводим результат на метку
                    } catch (ArithmeticException ex) {
                        resultLabel.setText(ex.getMessage()); // Выводим сообщение об ошибке, если она возникла
                    } catch (NumberFormatException ex) {
                        resultLabel.setText("Ошибка: Некорректные входные данные."); // Выводим сообщение об ошибке, если введены некорректные данные
                    }
                }
            });

            // Добавление элементов интерфейса на окно
            add(numberField1);
            add(operatorComboBox);
            add(numberField2);
            add(calculateButton);
            add(resultLabel);

//            pack(); // Автоматическое определение размеров окна на основе размеров элементов
            setSize(640, 480); // Установка статических размеров окна (ширина и высота)
            setLocationRelativeTo(null); // Окно будет по центру экрана
            setVisible(true); // Отображаем окно
        }

        // Метод для выполнения арифметической операции в зависимости от выбранного оператора
        private double calculateResult(double num1, double num2, String operator) throws ArithmeticException {
            CalculatorModel model = new CalculatorModel();

            switch (operator) {
                case "+":
                    return model.add(num1, num2);
                case "-":
                    return model.subtract(num1, num2);
                case "*":
                    return model.multiply(num1, num2);
                case "/":
                    return model.divide(num1, num2);
                default:
                    throw new IllegalArgumentException("Ошибка: Неверный оператор.");
            }
        }
    }

    // Презентер (Presenter) для калькулятора
    public static class CalculatorPresenter {
        private CalculatorView view;

        public CalculatorPresenter(CalculatorView view) {
            this.view = view;
        }

        public void start() {
            view.setVisible(true); // Показываем окно с интерфейсом калькулятора
        }
    }
}
