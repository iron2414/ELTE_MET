# beillesztes user tablaba
insert into user 
(
	name, 
	date_of_birth, 
	birth_place, 
	nationality, 
	permission, 
	username, 
	password, 
	bank_account_number, 
	tax_number, 
	degree, 
	which_semester
) 
values 
(
	"admin", 
	'1996.02.11', 
	"Budapest", 
	"Hungarian", 
	1, 
	"asd12", 
	"$2a$10$REwNTfatEEia7BuCj3riu.ZivsSAc6/UyCZAIPYAn9RpvueZVG3/W", 
	123456789, 
	123321, 
	"", 
	1
);

#beillesztes message tablaba
insert into message 
(
	sender_id,
    receiver_id,
    cc_id,
    content,
    sending_date
)
values
(
	2,
    1,
    1,
    "Kedves Admin, ezt nagyon szomoruan hallom hiszen mar lefestettem a csonakot, de majd legkozelebb. Udv, Dmitrij",
    current_timestamp()
);

# level lekerdezese adott userhez
select * from user as us
	inner join user_message_switch as ums
		on us.id = ums.user_id
			inner join message as ms
				on ms.id = ums.message_id
where us.id = 1;


# beillesztes document tablaba
insert into document
(
	code,
    doc_name,
    uploaded_date,
    name,
    download_counter,
    file_size,
    format,
    content,
    belong_to_course
)
values
(
	"xsdf44er",
    "Programozas 2EA.",
    '2017.11.23',
    2,
    11,
    2345,
    "PDF",
    load_file("/Users/attilagyen/Desktop/c++.docx"),
    "Programozas"
);

# doksik lekerdezese adott felhasznalohoz
select * from user as us
	inner join user_document_switch as uds
		on us.id = uds.user_id
			inner join document as doc
				on doc.id =  uds.document_id
where us.id = 2;

# tantargy felvetel
insert into subject
(
	name,
    credit_number,
    lectures_per_week,
    has_practice,
    is_necessary,
    recommended_semester,
    has_precondition,
    precondition_subject_id
)
values
(
	"Programozas eloadas",
    4,
    1,
    1,
    1,
    2,
    1,
    11
);

# gyakorlat felvetele 
insert into practice
(
	subject_id,
    teacher,
    credit_number,
    has_tasks,
    how_many_task
)
values
(
	2,
    2,
    2,
    0,
    0
);

# subject_teacher_teacher


insert into subject_semester_teacher
(
	subject_id,
    institution_teacher,
    starts_in_semester
)
values
(
	1,
    "Dmitrij Jakab",
    "2019/1"
);

# dds insert 
insert into dds
(
	date_time,
    durability,
    how_many_seats
)
values
(
	timestamp('2018-11-29 11:00:00'),
    90,
    25
);

select 
	us.name as 'Name',
    ex.name as 'Exam name',
    ex.created_by_name as 'Created by',
    ex.which_room as 'Room number',
    dds.date_time as 'When',
    dds.durability as 'How long in (min)',
    dds.how_many_seats as 'How many seats'
from user as us
	inner join user_exam_switch as ues
		on us.id = ues.user_id
			inner join exam as ex
				on ex.id = ues.exam_id
					inner join exam_dds_switch as eds
						on ex.id = eds.exam_id
							inner join dds 
								on dds.id = eds.dds_id

