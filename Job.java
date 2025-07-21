public class Job {
    public int id;
    public String title;
    public String[] requiredSkills;
    public int matchScore;

    public Job(int id, String title, String[] requiredSkills) {
        this.id = id;
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.matchScore = 0;
    }
}