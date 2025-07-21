import java.util.*;

public class JobMatcher {
    public static List<Job> getTopMatches(String resumeText, List<Job> allJobs) {
        Map<String, Integer> resumeSkillCount = ResumeProcessor.extractSkills(resumeText);
        PriorityQueue<Job> pq = new PriorityQueue<>((a, b) -> b.matchScore - a.matchScore);

        for (Job job : allJobs) {
            int score = 0;
            for (String skill : job.requiredSkills) {
                score += resumeSkillCount.getOrDefault(skill.toLowerCase(), 0);
            }
            job.matchScore = score;
            pq.offer(job);
        }

        List<Job> topMatches = new ArrayList<>();
        while (!pq.isEmpty() && topMatches.size() < 5) {
            Job job = pq.poll();
            if (job.matchScore > 0) {
                topMatches.add(job);
            }
        }

        return topMatches;
    }

    public static void main(String[] args) {
        String resumeText = "I have experience in Java, AWS, and data structures and algorithms. "
                          + "I also worked with MySQL and Spring Boot.";

        List<Job> allJobs = Arrays.asList(
            new Job(1, "Java Developer", new String[]{"Java", "Spring", "MySQL"}),
            new Job(2, "Cloud Engineer", new String[]{"AWS", "Linux", "CI/CD"}),
            new Job(3, "Backend Engineer", new String[]{"Java", "MySQL", "Data Structures"}),
            new Job(4, "Full Stack Developer", new String[]{"React", "Java", "AWS"}),
            new Job(5, "DSA Mentor", new String[]{"Data Structures", "Algorithms", "Java"})
        );

        List<Job> matched = getTopMatches(resumeText, allJobs);
        for (Job job : matched) {
            System.out.println(job.title + " - Match Score: " + job.matchScore);
        }
    }
}

