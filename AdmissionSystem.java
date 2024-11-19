/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdmissionSystem;



import java.util.ArrayList;
import java.util.List;

// Applicant Class
class Applicant {
    private String name;
    private int age;
    private double score;

    public Applicant(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getScore() {
        return score;
    }
}

// Filter Interface
interface Filter {
    boolean apply(Applicant applicant);
}

// Eligibility Filter
class EligibilityFilter implements Filter {
    @Override
    public boolean apply(Applicant applicant) {
        return applicant.getAge() >= 18; // Example eligibility criterion
    }
}

// Test Filter
class TestFilter implements Filter {
    @Override
    public boolean apply(Applicant applicant) {
        return applicant.getScore() >= 60; // Example test criterion
    }
}

// Interview Filter
class InterviewFilter implements Filter {
    @Override
    public boolean apply(Applicant applicant) {
        return applicant.getScore() >= 60; // Example interview criterion
    }
}

// Merit List Class
class MeritList {
    private List<Applicant> applicants = new ArrayList<>();

    public void addApplicant(Applicant applicant) {
        applicants.add(applicant);
    }

    public List<Applicant> getMeritList() {
        return applicants;
    }
}

// Observer Interface
interface Observer {
    void update(MeritList meritList);
}

// Merit List Observer
class MeritListObserver implements Observer {
    @Override
    public void update(MeritList meritList) {
        System.out.println("Updated Merit List:");
        for (Applicant applicant : meritList.getMeritList()) {
            System.out.println(applicant.getName());
        }
    }
}

// Result Observer
class ResultObserver implements Observer {
    @Override
    public void update(MeritList meritList) {
        System.out.println("Results have been updated.");
    }
}

// Admission Pipeline Class
class AdmissionPipeline {
    private List<Filter> filters = new ArrayList<>();
    private MeritList meritList = new MeritList();
    private List<Observer> observers = new ArrayList<>();

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void process(Applicant applicant) {
        boolean eligible = true;
        for (Filter filter : filters) {
            if (!filter.apply(applicant)) {
                eligible = false;
                break;
            }
        }
        if (eligible) {
            meritList.addApplicant(applicant);
            notifyObservers();
        }
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(meritList);
        }
    }

    public MeritList getMeritList() {
        return meritList;
    }
}

// Main Class
public class AdmissionSystem {
    public static void main(String[] args) {
        // Create the admission pipeline
        AdmissionPipeline pipeline = new AdmissionPipeline();

        // Add filters to the pipeline
        pipeline.addFilter(new EligibilityFilter());
        pipeline.addFilter(new TestFilter());
        pipeline.addFilter(new InterviewFilter());

        // Add observers to the pipeline
        MeritListObserver meritListObserver = new MeritListObserver();
        ResultObserver resultObserver = new ResultObserver();
        pipeline.addObserver(meritListObserver);
        pipeline.addObserver(resultObserver);

        // Create some applicants
        Applicant applicant1 = new Applicant("ZAIN", 20, 75);
        Applicant applicant2 = new Applicant("HAMZA", 17, 85); // Not eligible due to age
        Applicant applicant3 = new Applicant("AHMED", 19, 45); // Not eligible due to test score
        Applicant applicant4 = new Applicant("ALI", 22, 65); // Eligible

        // Process applicants through the admission pipeline
        System.out.println("Processing applicants...\n");
        pipeline.process(applicant1); // Should be added to the merit list
        pipeline.process(applicant2); // Should not be added
        pipeline.process(applicant3); // Should not be added
        pipeline.process(applicant4); // Should be added to the merit list

        // Final merit list
        System.out.println("\nFinal Merit List:");
        for (Applicant applicant : pipeline.getMeritList().getMeritList()) {
            System.out.println(applicant.getName());
        }
    }
}                                                        
