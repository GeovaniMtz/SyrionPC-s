package com.product.api.repository;

import com.product.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Category.
 * Proporciona métodos de acceso a datos para operaciones CRUD.
 */
public interface RepoCategory extends JpaRepository<Category, Integer> {

    /**
     * Obtiene todas las categorías ordenadas alfabéticamente.
     * @return Lista de todas las categorías
     */
    @Query(value = "SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> findAllOrdered();

    /**
     * Obtiene solo las categorías activas (status = 1) ordenadas alfabéticamente.
     * @return Lista de categorías activas
     */
    @Query(value = "SELECT * FROM category WHERE status = 1 ORDER BY category", nativeQuery = true)
    List<Category> findActive();

    /**
     * Busca una categoría por su nombre (case insensitive).
     * @param category Nombre de la categoría a buscar
     * @return Optional con la categoría si existe
     */
    @Query(value = "SELECT * FROM category WHERE LOWER(category) = LOWER(:category)", nativeQuery = true)
    Optional<Category> findByCategory(@Param("category") String category);

    /**
     * Busca una categoría por su tag (case insensitive).
     * @param tag Tag de la categoría a buscar
     * @return Optional con la categoría si existe
     */
    @Query(value = "SELECT * FROM category WHERE LOWER(tag) = LOWER(:tag)", nativeQuery = true)
    Optional<Category> findByTag(@Param("tag") String tag);

    /**
     * Busca una categoría por nombre excluyendo un ID específico.
     * Útil para validar duplicados al actualizar.
     * @param category Nombre de la categoría a buscar
     * @param id ID de la categoría a excluir de la búsqueda
     * @return Optional con la categoría si existe
     */
    @Query(value = "SELECT * FROM category WHERE LOWER(category) = LOWER(:category) AND category_id != :id", nativeQuery = true)
    Optional<Category> findByCategoryAndNotId(@Param("category") String category, @Param("id") Integer id);

    /**
     * Busca una categoría por tag excluyendo un ID específico.
     * Útil para validar duplicados al actualizar.
     * @param tag Tag de la categoría a buscar
     * @param id ID de la categoría a excluir de la búsqueda
     * @return Optional con la categoría si existe
     */
    @Query(value = "SELECT * FROM category WHERE LOWER(tag) = LOWER(:tag) AND category_id != :id", nativeQuery = true)
    Optional<Category> findByTagAndNotId(@Param("tag") String tag, @Param("id") Integer id);
}