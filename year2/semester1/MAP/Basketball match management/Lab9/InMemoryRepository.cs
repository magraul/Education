using System;
using System.Collections.Generic;
using System.Text;

namespace Lab9
{
    class InMemoryRepository<TIP_ID, TIP_ENTITATE> : IRepository<TIP_ID, TIP_ENTITATE> where TIP_ENTITATE : Entity<TIP_ID>
    {
        protected IDictionary<TIP_ID, TIP_ENTITATE> entities = new Dictionary<TIP_ID, TIP_ENTITATE>();

        public InMemoryRepository()
        {
           
        }

        public TIP_ENTITATE Delete(TIP_ID id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<TIP_ENTITATE> FindAll()
        {
            return entities.Values;
        }

        public TIP_ENTITATE FindOne(TIP_ID id)
        {
            try
            {
                return entities[id];
            }catch(Exception e)
            {
                return null;
            }
        }

        public TIP_ENTITATE Save(TIP_ENTITATE entity)
        {
            if (entity == null)
                throw new ArgumentNullException("entity must not be null");
          
            if (this.entities.ContainsKey(entity.Id))
            {
                return entity;
            }
            this.entities[entity.Id] = entity;
            return null;
        }

        public TIP_ENTITATE Update(TIP_ENTITATE entity)
        {
            throw new NotImplementedException();
        }
    }
}
