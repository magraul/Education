using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.repositories
{

    public class RepositoryException : ApplicationException
    {
        public RepositoryException() { }
        public RepositoryException(String mess) : base(mess) { }
        public RepositoryException(String mess, Exception e) : base(mess, e) { }
    }
    interface IRepository<TIP_ID, TIP_ENTITATE> where TIP_ENTITATE : entities.Entity<TIP_ID>
    {

		/// <summary>
		/// Finds an item
		/// </summary>
		/// <param name="id">The item id</param>
		/// <returns>The item if it exists, null otherwise</returns>
		/// 
		TIP_ENTITATE findOne(TIP_ID id);
		//E this[ID id] { get; set;}

		/// <summary>
		/// Gets all the items
		/// </summary>
		/// <returns>An enumerable containing all the items</returns>
		IEnumerable<TIP_ENTITATE> findAll();

		/// <summary>
		/// Saves an entity
		/// </summary>
		/// <param name="entity">The entity</param>
		/// <returns>The saved entity if we did not add. Null if the element was successfully added</returns>
		void save(TIP_ENTITATE entity);

		/// <summary>
		/// Deletes an entity
		/// </summary>
		/// <param name="id">The entity id</param>
		/// <returns>The removed entity</returns>
		void delete(TIP_ID id);

		/// <summary>
		///  Updates an entity
		/// </summary>
		/// <param name="entity">The entity</param>
		/// <returns>The updated entity</returns>
		//void update(E old, E entity);

		void update(TIP_ENTITATE entity);
	}
}
