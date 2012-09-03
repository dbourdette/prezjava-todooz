package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.util.TagCloud;

public class TagCloudServiceImpl implements TagCloudService {
	@Inject
	private SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public TagCloud buildTagCloud() {
		Session session = sessionFactory.getCurrentSession();

		TagCloud tagCloud = new TagCloud();

		List<String> entries = session.createQuery("select tags from Task").list();

		for (String tags : entries) {
			tagCloud.add(StringUtils.split(tags, ","));
		}

		return tagCloud;
	}
}
