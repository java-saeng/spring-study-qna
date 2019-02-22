package codesquad.service;

import codesquad.exception.NullQuestionException;
import codesquad.exception.WrongUserException;
import codesquad.model.answer.Answer;
import codesquad.model.answer.AnswerRepository;
import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NullQuestionException(id));
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public void update(Long id, Question newQuestion) {
        Question question = findById(id);
        question.update(newQuestion);
        questionRepository.save(question);
    }

    public void delete(Long id, User user) {
        isAuthority(id, user);
        questionRepository.deleteById(id);
    }

    public void save(Question question, String userId) {
        User writer = userRepository.findByUserId(userId);
        question.setWriter(writer);
        questionRepository.save(question);
    }

    public boolean isAuthority(Long questionId, User writer) {
        Question question = findById(questionId);
        if (!question.isWriter(writer)) {
            throw new WrongUserException(writer.getId());
        }
        return true;
    }

    public List<Answer> getAnswer(Long id) {
        Question question = findById(id);
        return question.getAnswers();
    }

    public int getAnswerSize(Long id) {
        List<Answer> answers = getAnswer(id);
        return answers.size();
    }

    public void saveAnswer(User user, Answer answer, Long questionId) {
        answer.setWriter(user);
        answer.setQuestion(findById(questionId));
        answer.setId(null);
        answerRepository.save(answer);
    }

    public void deleteAnswer(Long id, User user) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new RuntimeException());
        if (!answer.isWriter(user)) {
            throw new WrongUserException(user.getId());
        }
        answerRepository.delete(answer);
    }

}
