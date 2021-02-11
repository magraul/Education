using System;
using System.Collections.Generic;
using System.IO;

namespace Lab9
{
    public delegate TIP_ENTITATE CreateEntity<TIP_ENTITATE>(string line);

    public delegate string CreateLineFromEntity<TIP_ENTITATE>(TIP_ENTITATE entitate);


    abstract class AbstractFileRepository<TIP_ID, TIP_ENTITATE> : InMemoryRepository<TIP_ID, TIP_ENTITATE> where TIP_ENTITATE : Entity<TIP_ID>
    {
        protected string fileName;

        public AbstractFileRepository(string v, CreateEntity<TIP_ENTITATE> createEntity)
        {
            this.fileName = v;
            if (createEntity != null)
                loadFromFile(createEntity);
        }

        protected virtual void loadFromFile(CreateEntity<TIP_ENTITATE> createEntity)
        {        
            List<TIP_ENTITATE> list = DataReader.ReadData(fileName, createEntity);
            list.ForEach(x => entities[x.Id] = x);
        }

        public TIP_ENTITATE Save(TIP_ENTITATE entity)
        {
            TIP_ENTITATE a = base.Save(entity);
            if (a == null)
            {
                saveData();
                
            }
            return a;
        }

        private void saveData()
        {
            File.WriteAllText(Path.GetFullPath(this.fileName), string.Empty);
            List<string> forSave = new List<string>();

            foreach(TIP_ENTITATE e in entities.Values)
            {
                string linie = getLineFromEntity(e);
                forSave.Add(linie);
            }
            File.WriteAllLines(Path.GetFullPath(this.fileName), forSave);

        }

        public abstract string getLineFromEntity(TIP_ENTITATE e);
    }
}