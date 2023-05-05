import java.util.Arrays;

public class Rulebook {
    private Rule[] rules;

    public Rulebook() {
        this.rules = new Rule[0];
    }

    public Rule[] getRules() {
        return rules;
    }

    public void addRule(Rule rule) {
        Rule[] newRules = Arrays.copyOf(rules, rules.length + 1);
        newRules[rules.length] = rule;
        rules = newRules;
    }
    
    public void removeRule(Rule rule) {
        int index = -1;
        for (int i = 0; i < rules.length; i++) {
            if (rules[i].equals(rule)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            Rule[] newRules = new Rule[rules.length - 1];
            for (int i = 0; i < index; i++) {
                newRules[i] = rules[i];
            }
            for (int i = index + 1; i < rules.length; i++) {
                newRules[i - 1] = rules[i];
            }
            rules = newRules;
        }
    }

    public void clearAll() {
        rules = new Rule[0];
    }
}
