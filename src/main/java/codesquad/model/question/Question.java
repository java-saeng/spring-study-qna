package codesquad.model.question;

import codesquad.model.answer.Answer;
import codesquad.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;

    @OneToMany(mappedBy="question")
    private List<Answer> answers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public List<Answer> getAnswers(){
        return this.answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void update(Question question) {
        this.contents = question.contents;
        this.title = question.title;
    }

    public boolean isWriter(User user){
        if(this.writer.getId().equals(user.getId())){
            return true;
        }
        return false;
    }

}
