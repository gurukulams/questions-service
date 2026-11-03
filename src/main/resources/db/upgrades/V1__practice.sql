CREATE TABLE question (
  id UUID PRIMARY KEY,
  question TEXT NOT NULL,
  explanation TEXT NOT NULL,
  type VARCHAR(55) NOT NULL,
  answer VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by VARCHAR(55) NOT NULL,
  modified_at TIMESTAMP,
  modified_by VARCHAR(200)
);

CREATE TABLE question_localized (
    question_id UUID,
    locale VARCHAR(8) NOT NULL,
    question TEXT NOT NULL,
    explanation TEXT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question (id),
    PRIMARY KEY(question_id, locale)
);

CREATE TABLE question_choice (
    id UUID PRIMARY KEY,
    question_id UUID NOT NULL,
    "label" VARCHAR NOT NULL,
    answer BOOLEAN,
    FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE question_choice_localized (
    choice_id UUID,
    locale VARCHAR(8) NOT NULL,
    "label" VARCHAR NOT NULL,
    FOREIGN KEY (choice_id) REFERENCES question_choice (id),
    PRIMARY KEY (choice_id, locale)
);

CREATE TABLE answer (
  id UUID PRIMARY KEY,
  exam_id UUID NOT NULL,
  question_id UUID NOT NULL,
  student_answer VARCHAR(500) NOT NULL,
  FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE question_category (
    question_id UUID NOT NULL,
    category_id VARCHAR(55) NOT NULL,
    PRIMARY KEY(question_id, category_id),
    FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE question_tag (
    question_id UUID NOT NULL,
    tag_id VARCHAR(55) NOT NULL,
    PRIMARY KEY(question_id, tag_id),
    FOREIGN KEY (question_id) REFERENCES question (id)
);

-- 1. Create the exam table
CREATE TABLE exam (
  id UUID PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by VARCHAR(55) NOT NULL,
  modified_at TIMESTAMP,
  modified_by VARCHAR(200)
);

-- 2. Create the exam_questions junction table
CREATE TABLE exam_questions (
  exam_id UUID NOT NULL REFERENCES exam(id),
  question_id UUID NOT NULL REFERENCES question(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (exam_id, question_id)
);

-- 3. Add an index for performance when querying questions by exam or vice versa
CREATE INDEX idx_exam_questions_question_id ON exam_questions(question_id);