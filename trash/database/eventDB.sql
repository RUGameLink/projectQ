--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.1

-- Started on 2023-03-05 16:06:26

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 225 (class 1255 OID 57580)
-- Name: insert_events(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.insert_events() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
/*
SELECT count(*) as cnt FROM event_organisators
           WHERE (array_position(new.organisator, id) IS NOT NULL);
		   */
if((SELECT count(*) FROM event_organisators
           WHERE (array_position(new.organisator, id) IS NOT NULL)) = (select array_length(new.organisator, 1))) then
   RETURN new;
END IF;

RETURN null;

END
$$;


ALTER FUNCTION public.insert_events() OWNER TO postgres;

--
-- TOC entry 224 (class 1255 OID 57577)
-- Name: insert_orgs(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.insert_orgs() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF new.status in (
		select id from user_status
		where id = 1 
		) then
			insert INTO event_organisators (organisator, ismain) values(new.id, '0'), (new.id, '1');
	END IF;
RETURN NEW;

END
$$;


ALTER FUNCTION public.insert_orgs() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 57529)
-- Name: event_organisators; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event_organisators (
    id bigint NOT NULL,
    organisator bigint,
    ismain boolean DEFAULT false
);


ALTER TABLE public.event_organisators OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 57528)
-- Name: event_organisators_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.event_organisators_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.event_organisators_id_seq OWNER TO postgres;

--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 218
-- Name: event_organisators_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.event_organisators_id_seq OWNED BY public.event_organisators.id;


--
-- TOC entry 221 (class 1259 OID 57542)
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.events (
    id bigint NOT NULL,
    organisator bigint[],
    image text,
    title text,
    description text,
    tags text[],
    date timestamp without time zone,
    location text,
    isopen boolean DEFAULT true,
    regstrationdate daterange
);


ALTER TABLE public.events OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 57541)
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.events_id_seq OWNER TO postgres;

--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 220
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.events_id_seq OWNED BY public.events.id;


--
-- TOC entry 223 (class 1259 OID 57552)
-- Name: my_event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.my_event (
    id integer NOT NULL,
    event bigint,
    id_user bigint,
    presence boolean DEFAULT false,
    qr text,
    uid text
);


ALTER TABLE public.my_event OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 57551)
-- Name: my_event_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.my_event_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.my_event_id_seq OWNER TO postgres;

--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 222
-- Name: my_event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.my_event_id_seq OWNED BY public.my_event.id;


--
-- TOC entry 215 (class 1259 OID 57499)
-- Name: user_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_status (
    id smallint NOT NULL,
    status character varying(50)
);


ALTER TABLE public.user_status OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 57498)
-- Name: user_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_status_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_status_id_seq OWNER TO postgres;

--
-- TOC entry 3383 (class 0 OID 0)
-- Dependencies: 214
-- Name: user_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_status_id_seq OWNED BY public.user_status.id;


--
-- TOC entry 217 (class 1259 OID 57508)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    id_istu bigint,
    fullname text,
    studentgroup character varying(15),
    institute character varying(20),
    status smallint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 57507)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3384 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3197 (class 2604 OID 57532)
-- Name: event_organisators id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_organisators ALTER COLUMN id SET DEFAULT nextval('public.event_organisators_id_seq'::regclass);


--
-- TOC entry 3199 (class 2604 OID 57545)
-- Name: events id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events ALTER COLUMN id SET DEFAULT nextval('public.events_id_seq'::regclass);


--
-- TOC entry 3201 (class 2604 OID 57555)
-- Name: my_event id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.my_event ALTER COLUMN id SET DEFAULT nextval('public.my_event_id_seq'::regclass);


--
-- TOC entry 3195 (class 2604 OID 57502)
-- Name: user_status id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_status ALTER COLUMN id SET DEFAULT nextval('public.user_status_id_seq'::regclass);


--
-- TOC entry 3196 (class 2604 OID 57511)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3370 (class 0 OID 57529)
-- Dependencies: 219
-- Data for Name: event_organisators; Type: TABLE DATA; Schema: public; Owner: postgres
--




--
-- TOC entry 3372 (class 0 OID 57542)
-- Dependencies: 221
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3374 (class 0 OID 57552)
-- Dependencies: 223
-- Data for Name: my_event; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3366 (class 0 OID 57499)
-- Dependencies: 215
-- Data for Name: user_status; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- TOC entry 3368 (class 0 OID 57508)
-- Dependencies: 217
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3385 (class 0 OID 0)
-- Dependencies: 218
-- Name: event_organisators_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.event_organisators_id_seq', 6, true);


--
-- TOC entry 3386 (class 0 OID 0)
-- Dependencies: 220
-- Name: events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.events_id_seq', 17, true);


--
-- TOC entry 3387 (class 0 OID 0)
-- Dependencies: 222
-- Name: my_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.my_event_id_seq', 1, false);


--
-- TOC entry 3388 (class 0 OID 0)
-- Dependencies: 214
-- Name: user_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_status_id_seq', 4, true);


--
-- TOC entry 3389 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 8, true);


--
-- TOC entry 3212 (class 2606 OID 57535)
-- Name: event_organisators event_organisators_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_organisators
    ADD CONSTRAINT event_organisators_pkey PRIMARY KEY (id);


--
-- TOC entry 3214 (class 2606 OID 57550)
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- TOC entry 3216 (class 2606 OID 57560)
-- Name: my_event my_event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.my_event
    ADD CONSTRAINT my_event_pkey PRIMARY KEY (id);


--
-- TOC entry 3204 (class 2606 OID 57504)
-- Name: user_status user_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_status
    ADD CONSTRAINT user_status_pkey PRIMARY KEY (id);


--
-- TOC entry 3206 (class 2606 OID 57506)
-- Name: user_status user_status_status_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_status
    ADD CONSTRAINT user_status_status_key UNIQUE (status);


--
-- TOC entry 3208 (class 2606 OID 57517)
-- Name: users users_id_istu_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_id_istu_key UNIQUE (id_istu);


--
-- TOC entry 3210 (class 2606 OID 57515)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3222 (class 2620 OID 57581)
-- Name: events insert_event_before; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insert_event_before BEFORE INSERT ON public.events FOR EACH ROW EXECUTE FUNCTION public.insert_events();


--
-- TOC entry 3221 (class 2620 OID 57579)
-- Name: users insert_users_after; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER insert_users_after AFTER INSERT ON public.users FOR EACH ROW EXECUTE FUNCTION public.insert_orgs();


--
-- TOC entry 3218 (class 2606 OID 57536)
-- Name: event_organisators event_organisators_organisator_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_organisators
    ADD CONSTRAINT event_organisators_organisator_fkey FOREIGN KEY (organisator) REFERENCES public.users(id);


--
-- TOC entry 3219 (class 2606 OID 57561)
-- Name: my_event my_event_event_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.my_event
    ADD CONSTRAINT my_event_event_fkey FOREIGN KEY (event) REFERENCES public.events(id);


--
-- TOC entry 3220 (class 2606 OID 57566)
-- Name: my_event my_event_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.my_event
    ADD CONSTRAINT my_event_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.users(id);


--
-- TOC entry 3217 (class 2606 OID 57518)
-- Name: users users_status_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_status_fkey FOREIGN KEY (status) REFERENCES public.user_status(id);


-- Completed on 2023-03-05 16:06:26

--
-- PostgreSQL database dump complete
--

