import java.util.*;

public class ResumeProcessor {
    public static Map<String, Integer> extractSkills(String resumeText) {
        String[] skills = {"java", "mysql", "aws", "spring", "data structures", "algorithms"};
        Map<String, Integer> skillCount = new HashMap<>();

        resumeText = resumeText.toLowerCase();
        for (String skill : skills) {
            int count = resumeText.split(skill, -1).length - 1;
            if (count > 0) {
                skillCount.put(skill, count);
            }
        }

        return skillCount;
    }
}
