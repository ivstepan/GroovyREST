
INSERT INTO groovy.worker(name) VALUES ('John Doe')
INSERT INTO groovy.worker(name) VALUES ('Capitan Flint')
INSERT INTO groovy.worker(name) VALUES ('Big Green Man')
INSERT INTO groovy.worker(name) VALUES ('Fox Malder')

INSERT INTO groovy.skill(description, worker_id) VALUES ('green', 3)
INSERT INTO groovy.skill(description, worker_id) VALUES ('strong', 3)
INSERT INTO groovy.skill(description, worker_id) VALUES ('angry', 3)
INSERT INTO groovy.skill(description, worker_id) VALUES ('unidentified body', 1)
INSERT INTO groovy.skill(description, worker_id) VALUES ('one-legged', 2)
INSERT INTO groovy.skill(description, worker_id) VALUES ('one-eyed', 2)
INSERT INTO groovy.skill(description, worker_id) VALUES ('have a parrot', 2)
INSERT INTO groovy.skill(description, worker_id) VALUES ('good detective', 4)
INSERT INTO groovy.skill(description, worker_id) VALUES ('communicates with aliens', 4)

INSERT INTO groovy.task(title, location, date, is_resolved) VALUES ('Save the world', 'planet Earth', '23-04-2019', false)
INSERT INTO groovy.task(title, location, date, is_resolved) VALUES ('Capture the Queen Elizabeth Frigate', 'Сaribbean pool', '01-05-2019', false)
INSERT INTO groovy.task(title, location, date, is_resolved) VALUES ('Identify the body', 'hotel Сalifornia', '04-06-2019', false)

INSERT INTO groovy.task_worker(task_id, worker_id) VALUES (1, 3)
INSERT INTO groovy.task_worker(task_id, worker_id) VALUES (2, 2)
INSERT INTO groovy.task_worker(task_id, worker_id) VALUES (3, 1)
INSERT INTO groovy.task_worker(task_id, worker_id) VALUES (3, 4)