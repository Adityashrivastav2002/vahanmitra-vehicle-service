# VahanMitra — vehicle repair marketplace prototype

VahanMitra means “vehicle companion”. It is a responsive React prototype for finding vehicle repair at home, at a nearby garage, or on the road in India. The experience uses original sample branding and a simple service marketplace flow.

## Run locally

```bash
npm install
npm run dev
```

Open the local URL printed by Vite. The project uses React, Vite and Lucide icons.

## Backend API (Spring Boot)

The first backend slice is in `backend/`: Java 21+, Spring Boot 3.5, Spring Data JPA and a local H2 file database. The API seeds the four Delhi NCR demo shops on its first run and stores changes/bookings in `backend/data/`.

To start it on Windows, install a JDK (21 or later) and run this from the repository root:

```bash
cd backend
.\run.cmd
```

The launcher uses Maven from `PATH` when available. Otherwise, it downloads Maven 3.9.9 into `backend/.tools/` on its first run and uses that local copy. On macOS/Linux, install Maven 3.6.3+ and run `mvn spring-boot:run` from `backend/`.

The API listens on `http://localhost:8080`. H2 development console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:file:./data/vahanmitra`, user `sa`, blank password). The console is for local development only.

Initial endpoints:

- `GET /api/shops?area=Noida&query=bike` — browse shops and service menus.
- `GET /api/shops/{shopId}` — shop profile and full catalog.
- `POST /api/shops/{shopId}/services`, `PUT /api/shops/{shopId}/services/{serviceId}`, `DELETE /api/shops/{shopId}/services/{serviceId}` — manage a shop’s services and prices.
- `POST /api/bookings` — create a booking request from a listed service.
- `GET /api/bookings/{referenceCode}` and `GET /api/bookings?shopId={shopId}` — customer tracking and shop request list.
- `PATCH /api/bookings/{referenceCode}/status` — advance a request through allowed job states.

The API is an initial local development foundation and is not yet connected to the React screens. Routes currently have no real customer/shop authentication; do not expose them publicly before adding role-based authorization, OTP verification and production database secrets.

## What works in this demo

- Vehicle selection for cars, bikes and heavy vehicles.
- A two-step customer phone sign-in screen with demo OTP validation (use `123456`; no SMS is sent and no account is created).
- Service search, service category selection and partner sorting by rating or distance.
- Four sample shops across Delhi, Gurugram and Noida, each with ratings, INR estimates and an itemized service catalog. Open a shop to inspect service descriptions, prices and time estimates before booking.
- A Delhi NCR service-area selector; Delhi, Gurugram and Noida filter the sample partners.
- A two-step booking request modal with service mode and preferred time choices.
- A booking workspace with a request timeline and locally simulated partner, mechanic and job progress.
- A shop admin workspace with request handling, worker assignment, worker availability, and customer ratings for both the shop and individual mechanics.
- A shop service-catalog manager: select a sample shop, add/edit/remove catalog entries, and immediately see those changes in the customer-facing shop page for the current browser session.
- Save/unsave garage cards, category filters, text search and partner sorting.
- Responsive navigation and layouts for small screens.

All marketplace content, reviews, workers and bookings are local sample data. Service catalog changes are shared between admin and customer pages in browser memory for the current session and reset on reload. Customer login validates a fixed demo code (`123456`) locally; no OTP is delivered and no account is created. The city selector filters sample shops; it does not use geolocation or live distance calculations. Booking requests and status changes are held in browser memory and do not notify a shop. Timelines, response and arrival estimates and the map are illustrative. There is no live GPS, payment processing, real authentication, SMS/OTP provider, notification service, or backend API in this prototype. The separate shop admin experience is available at `/admin`.

## Proposed production architecture

### Frontend

- React + TypeScript with Vite for the customer web app; consider React Native for customer and mechanic mobile apps once the booking flow is validated.
- A separate shop operations portal for availability, catalog, quotes, job acceptance and status updates.
- Typed REST client, form validation and accessible component system. Use WebSockets or server-sent events for booking status and mechanic location updates when those services exist.

### Backend

- Java 21 + Spring Boot, split initially into clear modules: identity, vehicle profiles, partner/catalog, search, bookings, dispatch, job tracking, notifications and payments.
- PostgreSQL for users, partner locations, service catalogs, bookings, quotes and job state. Add PostGIS for nearby-partner queries; Redis for short-lived availability, rate limits and dispatch coordination.
- Spring Security with OAuth2/OIDC, role-based permissions for customers, mechanics, shops and operators. Store media in object storage and keep payment provider tokens out of application tables.
- REST APIs for browse, availability, booking and shop actions. Publish domain events through a broker such as Kafka or RabbitMQ as volume and operational needs justify it.
- Integrate a maps/geocoding provider for address resolution and routing, an Indian payment gateway for UPI/cards, and SMS/WhatsApp/push providers for notifications. Select vendors after cost, coverage and compliance review.

### Suggested booking lifecycle

`REQUESTED → ACCEPTED / REJECTED → MECHANIC_ASSIGNED → EN_ROUTE → ARRIVED → IN_PROGRESS → COMPLETED`

Support cancellation and rescheduling as explicit transitions. Keep the shop's estimated arrival and completion windows separate, timestamp every state change, and show customers when an estimate was last updated. For heavy-vehicle recovery, capture vehicle class, breakdown location, issue details, safe contact instructions and recovery equipment requirements before dispatch.

## Next implementation steps

1. Validate the customer, garage and roadside-assistance journeys with vehicle owners and partner shops.
2. Define launch city, service coverage, partner verification, pricing rules, cancellation policy and heavy-vehicle dispatch boundaries.
3. Create API contracts and database models for partners, service offerings, availability, bookings, quote approval and job events.
4. Implement authentication, partner onboarding and a shop acceptance portal; persist booking transitions in Spring Boot/PostgreSQL.
5. Add location and notifications after consent, permission handling, ETA quality and operational fallbacks are defined.
6. Integrate payments and refunds, then cover role permissions, booking state transitions, accessibility, mobile browser behavior and failure/retry paths.

## Project structure

```text
src/
  App.jsx       Marketplace UI and interactive prototype flows
  main.jsx      React entry point
  styles.css    Responsive visual system
index.html      Vite document shell
```
