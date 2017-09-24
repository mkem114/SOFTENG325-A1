package nz.ac.auckland.concert.service.services;

import nz.ac.auckland.concert.common.dto.ConcertDTO;
import nz.ac.auckland.concert.common.dto.PerformerDTO;
import nz.ac.auckland.concert.service.domain.Concert;
import nz.ac.auckland.concert.service.domain.Performer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to implement a simple REST Web service for managing Concerts.
 *
 */
@Path("/resource")
@Produces({MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_XML})
public class ConcertResource {

	private static Logger _logger = LoggerFactory.getLogger(ConcertResource.class);

    @GET
	@Path("/concerts")
	public Response getAllConcerts() {
		EntityManager em = PersistenceManager.instance().createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Concert> concertQuery = em.createQuery("select c from Concert c", Concert.class);
		List<Concert> concerts = concertQuery.getResultList();

		if (concerts.isEmpty()) {
			return Response.noContent().build();
		}

		Set<ConcertDTO> concertDTOs = new HashSet<>();
		for (Concert c : concerts) {
			concertDTOs.add(c.convertToDTO());
		}

		em.close();

		GenericEntity<Set<ConcertDTO>> wrapped = new GenericEntity<Set<ConcertDTO>>(concertDTOs) {
		};
		return Response.ok(wrapped).build();
	}

	@GET
	@Path("/performers")
	public Response getAllPerformers() {
		EntityManager em = PersistenceManager.instance().createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Performer> performerQuery = em.createQuery("select p from Performer p", Performer.class);
		List<Performer> performers = performerQuery.getResultList();

		if (performers.isEmpty()) {
			return Response.noContent().build();
		}

		Set<PerformerDTO> performerDTOs = new HashSet<>();
		for (Performer p : performers) {
			performerDTOs.add(p.convertToDTO());
		}

		em.close();

		GenericEntity<Set<PerformerDTO>> wrapped = new GenericEntity<Set<PerformerDTO>>(performerDTOs) {
		};
		return Response.ok(wrapped).build();
	}
//
//    /**
//     * Retrieves a Concert based on its unique id. The HTTP response message
//     * has a status code of either 200 or 404, depending on whether the
//     * specified Concert is found.
//     *
//     * When clientId is null, the HTTP request message doesn't contain a cookie
//     * named clientId (Config.CLIENT_COOKIE), this method generates a new
//     * cookie, whose value is a randomly generated UUID. This method returns
//     * the new cookie as part of the HTTP response message.
//     *
//     * This method maps to the URI pattern <base-uri>/concerts/{id}.
//     *
//     * @param id the unique ID of the Concert.
//     *
//     * @return a Response object containing the required Concert.
//     */
//    @GET
//    @Path("{id}")
//    public Response retrieveConcert(@PathParam("id") long id) {
//        EntityManager em = PersistenceManager.instance().createEntityManager();
//        em.getTransaction().begin();
//
//        Concert c = em.find(Concert.class, id);
//        em.getTransaction().commit();
//
//        if (c == null) {
//            throw new WebApplicationException(Response.Status.NOT_FOUND);
//        }
//
//        return Response.ok(c).build();
//    }
//
//    /**
//     * Creates a new Concert. This method assigns an ID to the new Concert and
//     * stores it in memory. The HTTP Response message returns a Location header
//     * with the URI of the new Concert and a status code of 201.
//     *
//     * When clientId is null, the HTTP request message doesn't contain a cookie
//     * named clientId (Config.CLIENT_COOKIE), this method generates a new
//     * cookie, whose value is a randomly generated UUID. This method returns
//     * the new cookie as part of the HTTP response message.
//     *
//     * This method maps to the URI pattern <base-uri>/concerts.
//     *
//     * @param concert the new Concert to create.
//     *
//     * @return a Response object containing the status code 201 and a Location
//     * header.
//     */
//    @POST
//    public Response createConcert(Concert concert) {
//        EntityManager em = PersistenceManager.instance().createEntityManager();
//        em.getTransaction().begin();
//
//        em.persist(concert);
//        em.getTransaction().commit();
//
//        return Response.created(URI.create("/concerts/" + concert.get_cID())).build();
//    }
//
//
//    @PUT
//    public Response updateConcert(Concert concert) {
//        EntityManager em = PersistenceManager.instance().createEntityManager();
//        em.getTransaction().begin();
//        Concert c = em.find(Concert.class, concert.get_cID());
//        em.getTransaction().commit();
//
//        if (c != null) {
//            em.getTransaction().begin();
//            em.merge(concert);
//            em.getTransaction().commit();
//
//            return Response.status(204).build();
//        }
//
//        return null;
//    }
//
//    /**
//     * Deletes all Concerts, returning a status code of 204.
//     *
//     * When clientId is null, the HTTP request message doesn't contain a cookie
//     * named clientId (Config.CLIENT_COOKIE), this method generates a new
//     * cookie, whose value is a randomly generated UUID. This method returns
//     * the new cookie as part of the HTTP response message.
//     *
//     * This method maps to the URI pattern <base-uri>/concerts.
//     *
//     * @return a Response object containing the status code 204.
//     */
//    @DELETE
//    @Path("{id}")
//    public Response deleteConcert(@PathParam("id") long id) {
//        EntityManager em = PersistenceManager.instance().createEntityManager();
//        em.getTransaction().begin();
//        Concert c = em.find(Concert.class, id);
//        em.getTransaction().commit();
//
//        if (c != null) {
//            em.getTransaction().begin();
//            em.remove(c);
//            em.getTransaction().commit();
//
//            return Response.status(204).build();
//        }
//
//        throw new WebApplicationException(Response.Status.NOT_FOUND);
//    }
//
//    /**
//     * Deletes all Concerts, returning a status code of 204.
//     *
//     * When clientId is null, the HTTP request message doesn't contain a cookie
//     * named clientId (Config.CLIENT_COOKIE), this method generates a new
//     * cookie, whose value is a randomly generated UUID. This method returns
//     * the new cookie as part of the HTTP response message.
//     *
//     * This method maps to the URI pattern <base-uri>/concerts.
//     *
//     * @return a Response object containing the status code 204.
//     */
//    @DELETE
//    public Response deleteAllConcerts() {
//        EntityManager em = PersistenceManager.instance().createEntityManager();
//        em.getTransaction().begin();
//        Query cq = em.createQuery("delete from Concert c");
//        cq.executeUpdate();
//        em.getTransaction().commit();
//        return Response.noContent().build();
//    }
}
