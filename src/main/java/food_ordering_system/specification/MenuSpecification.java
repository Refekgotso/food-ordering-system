package food_ordering_system.specification;

import food_ordering_system.entity.Menu;
import org.springframework.data.jpa.domain.Specification;

/**
 * MenuSpecification builds dynamic, reusable query conditions
 * (Specifications) for the Menu entity.
 *
 * Each method returns a Specification that represents ONE optional
 * filter condition. These are combined together in the service layer
 * so that any combination of filters (categoryId, search, or both,
 * or neither) works correctly without writing a separate repository
 * method for every possible combination.
 *
 * If a filter value is null or blank, the method returns null,
 * which tells Spring Data JPA to simply skip that condition.
 */
public class MenuSpecification {

    /**
     * Filters menus by categoryId. If categoryId is null,
     * this condition is skipped (returns null).
     */
    public static Specification<Menu> hasCategoryId(Long categoryId) {
        return (root, query, cb) ->
                categoryId == null
                        ? null
                        : cb.equal(root.get("category").get("id"), categoryId);
    }

    /**
     * Filters menus by a case-insensitive partial match on name.
     * If search is null or blank, this condition is skipped
     * (returns null).
     */
    public static Specification<Menu> nameContains(String search) {
        return (root, query, cb) ->
                (search == null || search.isBlank())
                        ? null
                        : cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%");
    }
}